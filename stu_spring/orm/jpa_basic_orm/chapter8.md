# 챕터8 프록시와 연관관계 관리

### 프록시

객체 그래프 탐색을 할 때, 정보들은 데이터베이스에 있기 때문에 모든 객체들을 자유롭게 탐색하긴 어렵다.

객체 그래프 탐색을 자유롭게 하려면 연관된 모든 객체를 가져와야 한다. 즉, 한번 DB에 접속했을 때 모든 정보를 가지고 와야 하는데, 서버에 큰 부담이 된다.

> 게다가 연관된 모든 엔티티들을 항상 사용하진 않는다.



그렇기 때문에 JPA는 엔티티가 실제 사용될 때 까지 조회를 미루는 지연 로딩을 지연하는데, 지연 로딩에서 사용되는 객체가 프록시 객체 이다.

> **JPA 표준은 지연 로딩의 구현을 JPA구현체에게 위임했다.**
>
> 즉, 지연 로딩의 구현은 JPA 구현체에 따라 바뀔 수 있다는 의미로, 여기선 **하이버네이트 구현체**를 사용한다.

### 프록시 기초

> 참고로 지연 객체는 프록시가 아니라 바이트 코드를 수정해서 사용할 수 있는데, 복잡하기 때문에 프록시만 사용한다.

JPA에서는 식별자로 엔티티를 조회할 때 `EntityManager.find()`를 사용한다.

이런 방식으로 조회하게 되면 영속성 컨텍스트에 값이 없으면 DB를 조회한다.



프록시를 사용해서 엔티티 사용 시점까지 조회를 미루고 싶다면 `EntityManager.getReference()`를 사용하면 된다.

이 메소드를 호출할 때 JPA는 DB를 조회하지 않고, 실제 엔티티 객체를 생성하지도 않는다. 단지 DB 접근을 위임한 프록시 객체를 반환한다.

#### 프록시 특징

![proxy](./images/proxy_characteristic.jpg)

프록시 클래스는 실제 클래스를 상속받아 만들기 때문에 겉모습은 똑같다.

그렇기 때문에 신경쓰지 않고 사용하면 된다.



프록시 객체는 실제 객체에 대한 참조를 보관한다.

![proxy](./images/proxy_structure.jpg)

그렇기 때문에 프록시 객체의 메소드를 호출하면 프록시 객체는 실제 객체의 메소드를 호출한다.

> Proxy.getId() -> Proxy.Entity.getId()

#### 프록시 객체의 초기화

프록시 객체는 실제 사용될 때 DB에 접근해서 초기화 한다.

``` java
Member member = em.getReference(Member.class, "id1");
member.getName();		// 이 시점에서 DB에 접근한다.
```

![proxy](./images/proxy_order.jpg)