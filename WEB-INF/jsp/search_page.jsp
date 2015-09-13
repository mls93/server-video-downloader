<%@ page import=" java.io.*" %>
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
    <form class='videorequest form form-urlencoded' method='post' action='postvideorequest'>
        <div>
            Автор<input class='videoartist' type='text' name='artist'>
        </div>
        <div>
            Название<input class='videotitle' type='text' name='title'>
        </div>
        <div>
            Доп. информация<input class='videoextra' type='text' name='extra'>
        </div>
        <div>
            <input type='submit' value='Найти видео'>
        </div>
    </form>
</body>
</html>
