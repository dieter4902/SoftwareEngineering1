package system.impl;

import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;
import system.DataRepository;

import java.util.Optional;

class DataRepositoryImpl implements DataRepository {

    CustomerRepository cRep;
    ArticleRepository aRep;
    OrderRepository oRep;


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

        @Override
        public Optional<Customer> findById(long id) {
            return Optional.empty();
        }

        @Override
        public Iterable<Customer> findAll() {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public Customer save(Customer entity) {
            return null;
        }
    }

    class ArticleRepositoryImpl implements ArticleRepository {

        @Override
        public Optional<Article> findById(String id) {
            return Optional.empty();
        }

        @Override
        public Iterable<Article> findAll() {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public Article save(Article entity) {
            return null;
        }
    }

    class OrderRepositoryImpl implements OrderRepository {

        @Override
        public Optional<Order> findById(String id) {
            return Optional.empty();
        }

        @Override
        public Iterable<Order> findAll() {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public Order save(Order entity) {
            return null;
        }
    }
}
