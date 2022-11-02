package ru.job4j.cars.model.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     */
    public void create(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("UPDATE User SET login = :fLogin, password = :fPassword WHERE id = :fId")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        List<User> result;
        Session session = sf.openSession();
        result = session
                .createQuery("FROM User u ORDER BY u.id", User.class)
                .list();
        session.close();
        return result;
    }

    /**
     * Найти пользователя по ID
     *
     * @param userId ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Optional<User> result;
        Session session = sf.openSession();
        result = session
                .createQuery("FROM User u WHERE u.id = :fId", User.class)
                .setParameter("fId", userId)
                .uniqueResultOptional();
        session.close();
        return result;
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        List<User> result;
        Session session = sf.openSession();
        result = session
                .createQuery("FROM User u WHERE login LIKE :fKey", User.class)
                .setParameter("fKey", "%" + key + "%")
                .list();
        session.close();
        return result;
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Optional<User> result;
        Session session = sf.openSession();
        result = session
                .createQuery("FROM User u WHERE u.login = :fLogin", User.class)
                .setParameter("fLogin", login)
                .uniqueResultOptional();
        session.close();
        return result;
    }
}
