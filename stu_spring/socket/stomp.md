### STOMP란

소켓은 그냥 메세지를 전달하는 채널의 개념인데, STOMP란 프로토콜의 도움을 받아 여러 작업을 할 수 있다.

> STOMP는 메세징에 최적화 되어 있다.

> STOMP(Simple Text Oriented Messaging Protocol)은 단순 텍스트 기반 프로토콜 이란 뜻을 가지고 있다.

<img src="https://postfiles.pstatic.net/MjAxNzA5MTRfMTc0/MDAxNTA1Mzk5MTEzNDY3.-wDLzIteitBFY8jw6LYaeyjmtDu7LZrF-n2V1rpAmKMg.XFChnVn95Jp54kiKoux1P1iAx9zMD-uuVX9RYkfIGmsg.PNG.scw0531/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2017-09-14_%EC%98%A4%ED%9B%84_11.02.42.png?type=w1" align="left">

> 위와 같은 형태로 구성되어 있다.

> 여기서 브로커란, MQ(Message Queue)이며, pub/sub 모델을 따른다.
>
> pub/sub 모델이란 메세지를 보내고/받는 형태의 통신을 말한다.
>
> <img src="https://blog.kakaocdn.net/dn/zb1ff/btqxHUs9IYP/pjjGHGQxdonmls45awISuK/img.png" alt="img" style="zoom:70%;" align="left" />
>
> 위 그림과 같이 토픽을 구독하고, 해당 토픽에서 메세지를 보내면, Broker가 구독한 사람들에게 메세지를 보내는 형태이다.
>
> 간단히 말하면 메세지를 보내는 측과 받는 측이 나누어져 있다면 pub/sub 모델이다.

> Topic이란, 모든 메세지들을 한곳에 모아두고 메세지를 가져올때마다 수많은 데이터중에 자기 메세지만 가져오는건 매우 비효율적이다.
>
> 그렇기 때문에 하나의 채팅방마다 저장소를 만들어 주고, 해당 저장소에 메세지를 보내면 나중에 토픽을 구독한 클라이언트들은 해당 저장소에서 메세지를 가져가면 된다.

- STOMP 예제

  > 메신저를 만든다고 가정했을 때, 채팅방을 생성하면 Topic이 생성된다.

  > 이렇게 만들어진 채팅방에 들어가게 되면, 그 Topic을 구독하게 되고, 브로커를 둔다.(요청을 받을 준비)

  > 그리고 그 채팅방에서 메세지를 보낼 수 있게 된다.