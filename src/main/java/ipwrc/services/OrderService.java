package ipwrc.services;

import ipwrc.models.Order;
import ipwrc.models.OrderItem;
import ipwrc.models.User;
import ipwrc.persistence.DAO;
import ipwrc.persistence.OrderDAO;
import ipwrc.persistence.ProductDAO;

import javax.ws.rs.NotFoundException;
import java.util.List;

public class OrderService extends BaseService<Order> {

    private ProductDAO productDAO;

    public OrderService(DAO<Order> dao, ProductDAO productDAO) {
        super(dao);
        this.productDAO = productDAO;
    }

    public List<Order> getPersonal(User user) {
        return ((OrderDAO)this.dao).getPersonal(user.getId());
    }

    public Order getPersonalById(User user, int id) {
        Order order = this.dao.getById(id);

        // Check if allowed
        if (!this.allowed(user, order))
            throw new NotFoundException();

        return order;
    }

    public void create(User user, Order obj) {
        obj.setUser(user);

        this.updateChildItems(obj);

        super.create(obj);
    }

    public void update(User user, Order obj) {
        obj.setUser(user);

        this.updateChildItems(obj);

        super.update(obj);
    }

    private void updateChildItems(Order obj) {
        List<OrderItem> items = obj.getItems();

        if (items.size() > 0) {
            // Bind the parent object
            for (OrderItem item : obj.getItems()) {
                // Fetch the real product from the db & set that
                item.setProduct(this.productDAO.getById(item.getProduct().getId()));

                item.setOrder(obj);
            }
        }
    }

    private boolean allowed(User user, Order obj) {
        if (user.hasRole("admin")) return true;

        return obj.getUser().getId() == user.getId();
    }
}
