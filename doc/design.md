# Project design Ideas

## About Login

About Inchat Unified Login interface design, design for small programs, apps, web-side login role, so will be token as the form of login Inchat websocket long connection, User server to do SSO authentication login after getting token to send login information directly to Inchat, the user server needs to override the Verifytoken method in Inchat to verify whether their token information is valid, normal start long connection.
In view of the token failure problem, WebSocket long connected login only for the first time login, then consider maintaining the link state (pingpong) in the form of heartbeat, the use of token authentication is to protect Inchat link routine (currently temporarily such design after changing the design according to usage)

![Image text](https://raw.githubusercontent.com/UncleCatMySelf/img-myself/master/img/design/%E6%9C%AA%E5%91%BD%E5%90%8D%E6%96%87%E4%BB%B6(12).png)