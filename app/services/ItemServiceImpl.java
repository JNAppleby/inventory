package services;

import org.springframework.transaction.annotation.Transactional;
import entities.Item;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

@Named
public class ItemServiceImpl implements ItemService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Item> getAllItems() {
        CriteriaQuery<Item> c = em.getCriteriaBuilder().createQuery(Item.class);
        c.from(Item.class);
        return em.createQuery(c).getResultList();
    }

    @Override
    @Transactional
    public Item addItem(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    @Transactional
    public boolean deleteItemById(Long id) {
        return false;
    }

    @Override
    @Transactional
    public Item editItem(Long id, String name, String desc) {
        Item item = em.find(Item.class, id);
        item.setName(name);
        item.setDesc(desc);
        em.merge(item);
        return null;
    }
}
