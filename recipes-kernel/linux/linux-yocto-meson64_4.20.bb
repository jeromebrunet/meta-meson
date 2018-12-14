#
# Linux Support for 64bit Amlogic Meson SoCs
#

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

#Just support the s400 for now
COMPATIBLE_MACHINE = "amlogic-s400"

SRCREV_meson = "7566ec393f4161572ba6f11ad5171fd5d59b0fbd"
LINUX_VERSION = "4.20-rc7"

# Linux stable tree
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=https;branch=master;name=meson \
	   file://0001-arm64-dts-meson-axg-fix-dtc-warning-about-unit-addre.patch \
	   file://0002-arm64-dts-meson-axg-s400-add-cts-rts-to-the-bluetoot.patch \
	   file://0003-arm64-dts-meson-axg-add-secure-monitor.patch \
	   file://0004-arm64-dts-meson-axg-fix-mailbox-address.patch \
	   file://0005-arm64-dts-meson-axg-correct-sram-shared-mem-unit-add.patch \
	   file://0006-arm64-dts-meson-axg-enable-SCPI.patch \
	   file://0007-arm64-dts-meson-s400-add-bcm-bluetooth-device.patch \
	   file://0008-ASoC-meson-axg-toddr-add-support-for-spdifin-backend.patch \
	   file://0009-ASoC-meson-add-axg-spdif-input-DT-binding-documentat.patch \
	   file://0010-ASoC-meson-add-axg-spdif-input.patch \
	   file://0011-mmc-meson-gx-make-sure-the-descriptor-is-stopped-on-.patch \
	   file://0012-mmc-meson-gx-remove-useless-lock.patch \
	   file://0013-mmc-meson-gx-align-default-phase-on-soc-vendor-tree.patch \
	   file://0014-mmc-meson-gx-add-signal-resampling.patch \
	   file://0015-arm64-dts-meson-s400-enable-sdr104-on-sdio.patch \
	   file://0016-arm64-dts-meson-s400-fix-emmc-maximum-rate.patch \
	   file://0017-ASoC-meson-fix-do_div-warning-in-spdifin.patch \
	   file://0018-arm64-defconfig-enable-modules-for-amlogic-s400-soun.patch \
           file://defconfig \
           "

KERNEL_VERSION_SANITY_SKIP="1"

KERNEL_CLASSES = "kernel-uimage-meson"

LINUX_VERSION_EXTENSION_append = "-meson64"

require linux-meson.inc

# Checksum changed on 4.17
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

