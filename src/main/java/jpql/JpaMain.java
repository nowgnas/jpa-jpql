package jpql;

import javax.persistence.*;

public class JpaMain {
    public static void main(String[] args) {
        // EntityManagerFactor는 서버 시작 시 애플리케이션 전체에 하나만 생성됨
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜잭션 시작
        try {
            Member member = new Member();
            member.setUsername("user1");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = :username", Member.class);
            query.setParameter("username", "user1");
            Member singleResult = query.getSingleResult();
            System.out.println("single result: " + singleResult);


//            // 타입 정보는 엔티티로 줘야 한다
//            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
//            // 반환 값 타입을 정할 수 있다
//            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//            // 타입 정보를 받을 수 없을 때 사용
//            Query query1 = em.createQuery("select m.username, m.age from Member m");
//

            tx.commit(); // 트랜잭션 커밋

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();

        }

        // -------------
        emf.close();
    }
}
