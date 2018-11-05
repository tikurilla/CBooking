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
    <form action="/checkout/${auditorium.ID}" method="POST" >
       <p>Please choose seats in auditorium ${auditorium.auditoriumName}:</p>
       <#list auditorium.occupiedSeatsMap as seatNumber, seatStatus>
            <#if seatStatus == "checked">
                <input type="checkbox" name="seats" value=${seatNumber} checked disabled>${seatNumber}<Br>
            <#else>
                <input type="checkbox" name="seats" value=${seatNumber}>${seatNumber}<Br>
            </#if>
       </#list>

       <p><input type="submit" value="Reserve"></p>
      </form>
</body>
</html>