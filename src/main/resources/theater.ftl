<!DOCTYPE html>
<html>
<head>
  <style type="text/css"> <#include "theatersstyle.css"> </style>
  <title>Welcome!</title>
</head>
<header>
    <h1>Theater ${movieTheater.movieTheaterName}</h1>
    <nav><a href="/theaters">To theaters list</a></nav>
</header>
<body>
    <p>Halls number in this theater - ${movieTheater.auditoriumAmount}: </p>
    <ul type="square">
        <#list movieTheater.auditoriumNamesMap as auditoriumID, auditoriumName>
            <li><a href="/auditorium/${auditoriumID}">${auditoriumName}</a></li>
        </#list>
    </ul>
</body>
</html>
