#!/bin/bash
###############################################################################
#- File: savetyping.sh
#- Purpose: set up a CS 246 java project development environment
###############################################################################

#- declare and export shared environment variables
export EDITOR=emacs
export PH=${PROJECTHOME:=$PWD}
export PRJCT=${PROJECT:=`basename $PH`}
export PRKEY=${PROJECTKEY:=`echo $PRJCT | tr "[:upper:]" "[:lower:]"`}
export BUILDDIR=$PH/build
export PP1=${PRKEY}.desktop
export PP2=${PRKEY}.service
export PP3=${PRKEY}.system
export SHARED=$HOME/cs246
export SHAREDBIN=$SHARED/bin
export SHAREDLIB=$SHARED/lib
export PATH=$SHAREDBIN:$PATH
export CP=$BUILDDIR/class:$BUILDDIR/test:$PH:$PH/src:$PH/test:$PH/lib/\*:$SHAREDLIB/\*
export CLASSPATH=$CP

# show help
h()
{
   echo -n export > tmpA$$ && echo ' -f' >> tmpA$$
   echo -n \#> tmpB$$ && echo ' ' >> tmpB$$
   grep -f tmpB$$ $PH/sdk/savetyping.sh | cut -d" " -f2- > tmpC$$
   grep -f tmpA$$ $PH/sdk/savetyping.sh | cut -d" " -f3- | paste - tmpC$$
   rm tmpA$$ tmpB$$ tmpC$$
}
export -f h
# make all build directories (if necessary)
mbd()
{
   mkdir -p $BUILDDIR/class
   mkdir -p $BUILDDIR/jar
   mkdir -p $BUILDDIR/jdoc/resources
   mkdir -p $BUILDDIR/test
}
export -f mbd
# connect to the project home directory
ph()
{
   cd $PH
}
export -f ph
# connect to a module home directory given the module name (outer.inner)
mh()
{
   outer=`echo $1 | cut -d . -f1`
   inner=`echo $1 | cut -d . -f2`
   cd $PH/src/$outer/$inner
}
export -f mh
# connect to the system module home directory
sy()
{
   mh $PP3
}
export -f sy
# connect to the service module home directory
se()
{
   mh $PP2
}
export -f se
# connect to the desktop module home directory
de()
{
   mh $PP1
}
export -f de
# build java source files in a module
b()
{
   BASE=`basename $PWD`
   if [ "$BASE" = "$PRJCT" ]
   then
      b1
      if [ -f $PH/src/Run.java\
           -a $PH/src/Run.java -nt $BUILDDIR/class/Run.class ]
      then
         javac $* -d $BUILDDIR/class $PH/src/Run.java
      fi
   else
      JAR=$BUILDDIR/jar/${PRKEY}${BASE}.jar
      if [ -f $JAR ]; then
	 FILES=`find . -name "*.java" -newer $JAR | tr "\n" " "`
      else
	 FILES=`find . -name "*.java" | tr "\n" " "`
      fi
      if [ -z "$FILES" ]; then
	 echo "Nothing needs building in $BASE"
      else
	 echo "Building $FILES"
	 javac $* -d $BUILDDIR/class $FILES &&\
	    jar cf $JAR -C $BUILDDIR/class $PRKEY/$BASE &&\
	    echo "$JAR built."
      fi
   fi
}
export -f b
# connect to the system module home directory and build it
syb()
{
   sy
   b
}
export -f syb
# connect to the service module home directory and build it
seb()
{
   se
   b
}
export -f seb
# connect to the desktop module home directory and build it
deb()
{
   de
   b
}
export -f deb
# build all modules and then connect to the project home directory
b1()
{
   syb && seb && deb && ph
}
export -f b1
# build all modules, connect to the project home directory and make the project jar
b2()
{
   b1 && mj
}
export -f b2
# build all modules, connect to the project home, and run via command line
r1()
{
   b1 && rc
}
export -f r1
# build all modules, connect to the project home directory, make the project jar and run using the jar
r2()
{
   b2 && rj
}
export -f r2
# wipe (clean up, or remove) all build artifacts
wipe()
{
   rm -rf $BUILDDIR
   mbd
}
export -f wipe
# make jar
mj()
{
   MF=$1
   if [ -z "$MF" ]
   then
     MF=$PH/sdk/manifest.mf
   fi
   if [ -z "$2" ]
   then
      cp -r resources $BUILDDIR
      rm -rf $BUILDDIR/resources/.svn
      rm -rf $BUILDDIR/resources/images/.svn
      rm -rf $BUILDDIR/resources/properties/.svn
   else
      svnurl=`svn info | grep URL | cut -d" " -f2`
      svn -q export $svnurl/resources $BUILDDIR/resources
   fi
   if [ -f "$MF" ]
   then
      jar cfm $PRKEY.jar $MF -C $BUILDDIR/class . -C $BUILDDIR resources
   else
      jar cfe $PRKEY.jar Run -C $BUILDDIR/class . -C $BUILDDIR resources
   fi
   rm -rf $BUILDDIR/resources
}
export -f mj
# run project from command line
rc()
{
   java $* Run
}
export -f rc
# run project from jar
rj()
{
   java $* -jar $PRKEY.jar
}
export -f rj
# source file packager
sfp()
{
    java -Duser.dir=$PH/src/$PRKEY -jar $SHAREDLIB/sfp.jar
}
export -f sfp
# makes all .jpg files from all .uxf files in $PH/docs
jpg()
{
   for f in $PH/docs/*.uxf; do \
      java -jar $SHARED/Umlet/umlet.jar \
         -action=convert -format=jpg -filename=$f; \
      output=`basename $f .uxf`.jpg
      mv $PH/docs/$output $BUILDDIR/jdoc/resources
   done
}
export -f jpg
# generate javadocs
jdoc()
{
   cd $PH
   javadoc -quiet -author -link http://docs.oracle.com/javase/7/docs/api/\
       -d build/jdoc -sourcepath src -classpath build/class\
       -extdirs lib -private -subpackages $PP1 $PP2 $PP3
}
export -f jdoc
# generate a key store
gks()
{
   keytool -genkey -keystore $BUILDDIR/jar/${PRKEY}keystore -alias myself
}
export -f gks
# sign jar with a self-signed certificate
sj()
{
   jarsigner -keystore $BUILDDIR/jar/${PRKEY}keystore \
      -storepass ${PRKEY}keystore -keypass ${PRKEY}keystore \
      $PH/$PRKEY.jar myself
}
export -f sj
# show all TODOs
todo()
{
    cd $PH
    find . -name "*.java" | xargs grep -n -i todo
}
export -f todo
# show who I am
whoisme()
{
   pinky -f `whoami` | awk '{print $2 " " $3}'
}
export -f whoisme
# show my TODOs
mytodo()
{
   todo | grep -i `whoisme`
}
export -f mytodo
###############################################################################
#- End of savetyping.sh project setup file
###############################################################################
