#Video Player and Downloader
Simple Tomcat web application which can download video from Youtube - 1st link according to search parameters.
It downloads video with highest quality and saves it in *videos* folder.

To run it, you need to create new webapp and save source files in it.
It can be compiled using *Intellij Idea*(using *maven*, pom.xml in WEB-INF folder)

When running server, you need to add some system properties (*videoplayer.videos* and *videoplayer.root*), e.g., to *bat* or *sh* file which starts *tomcat*
`-Dvideoplayer.videos=<directory of videos relative to catalina.base> -Dvideoplayer.root=<directory of this webapp relative to catalina.base>` 
 
