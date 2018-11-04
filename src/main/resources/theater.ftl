<!DOCTYPE html>
<html>
<head>
  <style type="text/css"> <#include "theatersstyle.css"> </style>
  <title>Welcome!</title>
</head>
<header>
    <h1>Кинотеатр ${movieTheater.movieTheaterName}</h1>
    <nav><a href="/theaters">К списку кинотеатров</a></nav>
</header>
<body>
    <p>Количество залов в кинотеатре - ${movieTheater.auditoriumAmount}: </p>
    <ul type="square">
        <#list movieTheater.auditoriumNamesMap as auditoriumID, auditoriumName>
            <li><a href="/auditorium/${auditoriumID}">${auditoriumName}</a></li>
        </#list>
    </ul>
</body>
</html>
