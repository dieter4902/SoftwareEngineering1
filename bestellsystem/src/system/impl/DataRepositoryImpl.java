package system.impl;

import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;
import system.DataRepository;

import java.util.HashMap;
import java.util.Optional;

class DataRepositoryImpl implements DataRepository {

    CustomerRepository cRep;
    ArticleRepository aRep;
    OrderRepository oRep;

    public DataRepositoryImpl() {
        cRep = new CustomerRepositoryImpl();
        aRep = new ArticleRepositoryImpl();
        oRep = new OrderRepositoryImpl();
    }

    public CustomerRepository getCustomerRepository() {
        return cRep;
    }

    public ArticleRepository getArticleRepository() {
        return aRep;
    }

    public OrderRepository getOrderRepository() {
        return oRep;
    }

    class CustomerRepositoryImpl implements CustomerRepository {

        private HashMap<Long, Customer> CustomerList;

        public CustomerRepositoryImpl() {
            CustomerList = new HashMap<>();
        }

        @Override
        public Optional<Customer> findById(long id) {
            Customer c = CustomerList.get(id);
            return c == null ? Optional.empty() : Optional.of(c);
        }

        @Override
        public Iterable<Customer> findAll() {
            return CustomerList.values();
        }

        @Override
        public long count() {
            return CustomerList.size();
        }

        @Override
        public Customer save(Customer entity) {
            CustomerList.putIfAbsent(entity.getId(), entity);
            return entity;
        }
    }

    class ArticleRepositoryImpl implements ArticleRepository {
        private HashMap<String, Article> ArticleList;

        public ArticleRepositoryImpl() {
            ArticleList = new HashMap<>();
        }

        @Override
        public Optional<Article> findById(String id) {
            Article a = ArticleList.get(id);
            return a == null ? Optional.empty() : Optional.of(a);
        }

        @Override
        public Iterable<Article> findAll() {
            return ArticleList.values();
        }

        @Override
        public long count() {
            return ArticleList.size();
        }

        @Override
        public Article save(Article entity) {
            ArticleList.putIfAbsent(entity.getId(), entity);
            return entity;
        }
    }

    class OrderRepositoryImpl implements OrderRepository {

        private HashMap<String, Order> OrderList;

        public OrderRepositoryImpl() {
            OrderList = new HashMap<>();
        }

        @Override
        public Optional<Order> findById(String id) {
            Order o = OrderList.get(id);
            return o == null ? Optional.empty() : Optional.of(o);
        }

        @Override
        public Iterable<Order> findAll() {
            return OrderList.values();
        }

        @Override
        public long count() {
            return OrderList.size();
        }

        @Override
        public Order save(Order entity) {
            OrderList.putIfAbsent(entity.getId(), entity);
            return entity;
        }
    }
}
