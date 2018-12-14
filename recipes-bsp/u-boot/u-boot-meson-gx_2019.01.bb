require u-boot-common_${PV}.inc
require recipes-bsp/u-boot/u-boot.inc

DEPENDS += "bison-native bc-native dtc-native python-native amlogic-fip"

PROVIDES = "u-boot"

# Just support the S400 for now
COMPATIBLE_MACHINE = "amlogic-s400"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/${PV}:"

SRC_URI_append = " \
	file://0001-arm-dts-s400-Fix-status-for-eMMC-and-SDIO-ports.patch \
	file://0002-board-meson-s400-enable-eMMC-when-booting-from-it.patch \
	"

deploy_axg () {
    FIPDIR="${DEPLOY_DIR_IMAGE}/fip/"
    DESTDIR="${B}/fip"

    mkdir -p ${DESTDIR}

    cp ${FIPDIR}/bl31.img ${DESTDIR}/bl31.img
    cp ${B}/u-boot.bin ${DESTDIR}/bl33.bin

    ${FIPDIR}/blx_fix.sh ${FIPDIR}/bl30.bin \
			 ${DESTDIR}/zero_tmp \
			 ${DESTDIR}/bl30_zero.bin \
			 ${FIPDIR}/bl301.bin \
			 ${DESTDIR}/bl301_zero.bin \
			 ${DESTDIR}/bl30_new.bin bl30
    python ${FIPDIR}/acs_tool.pyc ${FIPDIR}/bl2.bin \
				  ${DESTDIR}/bl2_acs.bin \
				  ${FIPDIR}/acs.bin 0
    ${FIPDIR}/blx_fix.sh ${DESTDIR}/bl2_acs.bin \
			 ${DESTDIR}/zero_tmp \
			 ${DESTDIR}/bl2_zero.bin \
			 ${FIPDIR}/bl21.bin \
			 ${DESTDIR}/bl21_zero.bin \
			 ${DESTDIR}/bl2_new.bin bl2

    ${FIPDIR}/aml_encrypt --bl3sig --input ${DESTDIR}/bl30_new.bin --output ${DESTDIR}/bl30_new.bin.enc --level 3 --type bl30
    ${FIPDIR}/aml_encrypt --bl3sig --input ${DESTDIR}/bl31.img --output ${DESTDIR}/bl31.img.enc --level 3 --type bl31
    ${FIPDIR}/aml_encrypt --bl3sig --input ${DESTDIR}/bl33.bin --output ${DESTDIR}/bl33.bin.enc --level 3 --type bl33 --compress lz4
    ${FIPDIR}/aml_encrypt --bl2sig --input ${DESTDIR}/bl2_new.bin --output ${DESTDIR}/bl2.n.bin.sig
    ${FIPDIR}/aml_encrypt --bootmk --output ${DESTDIR}/u-boot.bin \
			  --bl2 ${DESTDIR}/bl2.n.bin.sig \
			  --bl30 ${DESTDIR}/bl30_new.bin.enc \
			  --bl31 ${DESTDIR}/bl31.img.enc \
			  --bl33 ${DESTDIR}/bl33.bin.enc \
			  --level 3

    # SDCard
    install ${DESTDIR}/u-boot.bin.sd.bin ${DEPLOYDIR}/u-boot.bin.sd.bin
    # eMMC
    install ${DESTDIR}/u-boot.bin ${DEPLOYDIR}/u-boot.bin
}

DEPLOY_COMMAND_meson-axg = "deploy_axg"

do_deploy_append_meson-gx () {
	${DEPLOY_COMMAND}
}
