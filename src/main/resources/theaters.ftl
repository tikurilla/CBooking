<!DOCTYPE html>
<html>
<head>
  <style type="text/css"> <#include "theatersstyle.css"> </style>
  <title>Welcome!</title>
</head>
<header>
    <h1>Theaters list</h1>
    <nav><a href="/theaters">To theaters list</a></nav>
</header>
<body>
    <ul type="square">
    <#list movieTheaters as theater>
        <li><a href="/theater/${theater.movieTheaterID}">${theater.movieTheaterName}</a> - ${theater.auditoriumAmount} halls</li>
    </#list>
    </ul>
</body>
</html>
