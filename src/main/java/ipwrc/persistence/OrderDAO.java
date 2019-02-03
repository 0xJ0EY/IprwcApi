package ipwrc.persistence;

import ipwrc.models.Order;
import ipwrc.models.Product;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDAO extends DAO<Order> {

    public OrderDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getAll() {
        return list((Query<Order>) namedQuery("Order.getAll"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Order getById(int id) {
        List<Order> orders = list((Query<Order>) namedQuery("Order.findById")
                .setParameter("id", id)
                .setMaxResults(1)
        );

        return orders.size() > 0 ? orders.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    public List<Order> getPersonal(int id) {
        return list((Query<Order>) namedQuery("Order.findPersonal")
                .setParameter("id", id));
    }

}
