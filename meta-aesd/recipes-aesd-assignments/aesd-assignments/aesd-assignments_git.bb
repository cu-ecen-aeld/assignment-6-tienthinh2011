# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
#LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
#LIC_FILES_CHKSUM = ""

# TODO: Set this  with the path to your assignments rep.  Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access
# SRC_URI = "git@github.com:cu-ecen-aeld/assignments-3-and-later-tienthinh2011.git;protocol=ssh"
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-tienthinh2011.git;branch=assignment6-part1;protocol=ssh"
#PV = "1.0+git${SRCPV}"
# TODO: set to reference a specific commit hash in your assignment repo
SRCREV = "6da1384da51e728bddf556f5bb3d097329ef3cdf"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

# TODO: Add the aesdsocket application and any other files you need to install
FILES:${PN} += "${bindir}/aesdsocket"
#inherit update-rc.d
# Define the init script name and parameters
INITSCRIPT_PACKAGES = "${PN}" #{PN} = "aesd-assignment"
INITSCRIPT_NAME = "aesdsocket-start-stop"
INITSCRIPT_PARAMS = "defaults"

# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
#FILES:${PN} += "${bindir}/aesdsocket" #This one is for 
# TODO: customize these as necessary for any libraries you need for your application
# (and remove comment)
TARGET_LDFLAGS += "-lpthread -lrt"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
	install -d ${D}${bindir} #create the {binddir} in destination folder {D} if needed
	install -m 0755 ${S}/aesdsocket ${D}${bindir}/ #copy file

	# Add initscript
	# Install the init script
    	install -d ${D}${sysconfdir}/init.d
    	install -m 0755 ${S}/${INITSCRIPT_NAME} ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
}
