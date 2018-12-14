HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"
PE = "1"

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "d452f27b3ea806fd99aee4b73a723318032c1d5c"

SRC_URI = "git://git.denx.de/u-boot.git"

S = "${WORKDIR}/git"
