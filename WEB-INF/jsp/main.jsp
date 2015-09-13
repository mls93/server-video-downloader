<%@ page import="java.util.*, java.io.*, java.nio.file.Paths, parsing.MyMP4Parser "%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>
        VideoPlayer
    </title>
	<link data-require="bootstrap@3.3.1" data-semver="3.3.1" rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" />
    <script data-require="jquery@2.1.4" data-semver="2.1.4" src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
    <script data-require="bootstrap@3.3.1" data-semver="3.3.1" src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
    <script src="/MyVideoPlayer/script.js"></script>
    <link rel="stylesheet" href="/MyVideoPlayer/style.css" />
</head>
 
<body>
	
	<%!
		File catalinaBase = new File( System.getProperty( "catalina.base" ) ).getAbsoluteFile();
		String videoFolderLink = System.getProperty("videoplayer.videos");

		File videoFolder = new File(catalinaBase,videoFolderLink);
		List<String> names = new ArrayList<String>();		
		public List<String> listFilesForFolder(final File folder) {
			List<String> video_array = new ArrayList<String>();
			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory()) {
					video_array.addAll(listFilesForFolder(fileEntry));
				} else {
					video_array.add(folder.getName()+"/"+fileEntry.getName());
				}
			}
			return video_array;
		}
	%>

	<h1 align='center'>Мои видео</h1>
	<div>
		<h4>Названия</h4>
		<div class="tabbable tabs-left">
			<ul class="nav nav-tabs">
						
		<%  int count = 0;
			for (String s:listFilesForFolder(videoFolder)){
            	count++;
            	MyMP4Parser parser = new MyMP4Parser( System.getProperty( "catalina.base" )+ System.getProperty("videoplayer.root") +s);
            	String name = s;
            	try {
                	name = parser.getDetails();
            	}
            	catch (Exception e){

            }
            names.add(name);
            out.println("<li class='li_video' id='li_video"+count+"'><a href='#video"+count+"' data-toggle='tab'>"+name+"</a></li>");

        }


		%>
		 
			</ul>
			<div class="tab-content">

			<%	count = 0;
				for (String s:listFilesForFolder(videoFolder)){
					count++;
					out.println("<div class='tab-pane' id='video"+count+"'>");
					out.println("<table class='table table-striped' style='width:70%;'><thead><th>Видео</th><th><th>Слова</th></thead>");
					out.println("<tbody><tr>");
					out.println("<td><div id='descr"+count+"' style='display:none;'>/MyVideoPlayer/"+s+"</div><div class='video_td' id='video_td"+count+"'>");
					out.println("<a href='/MyVideoPlayer/"+s+"'></a></div></td>");
					out.println("<td id='videowords"+count+"'>");
					out.println("<form class='wordretrieve' action='RetrieveWords'>");
					out.println("<input class='videotitle' id='videotitle"+count+"' type='hidden' name='title' value='"+names.get(count-1)+"'>");
					out.println("<input type='submit' value='Найти слова'>");
					out.println("</form></td></tr></tbody></table></div>");
				}

			%>

			</div>
		</div>
	</div>
	<center>
		<label><input id='find_vid' type='checkbox'>Найти видео</label>
		<div id='div_add_video' style='display:none;'>
			<form method='post' action='postvideorequest' id='add_video'>
				<table>
					<tbody>
						<tr>
							<td>
								<label for='vid_artist'>Исполнитель</label>
							</td>
							<td>
								<input id='vid_artist' type='text' name='artist'>
							</td>
						</tr>
						<tr>
							<td>
								<label for='vid_title'>Название</label>
							</td>
							<td>
								<input id='vid_title' type='text' name='title'>
							</td>
						</tr>
						<tr>
							<td>
								<label for='vid_extra'>Доп. информация</label>
							</td>
							<td>
								<input id='vid_extra' type='text' name='extra'>
							</td>
						</tr>
					</tbody>
				</table>
				<div>
					<input id='add_vid_submit' type='submit' value='Найти видео'>
				</div>
			</form>
		</div>
	</center>
</body>
</html>
