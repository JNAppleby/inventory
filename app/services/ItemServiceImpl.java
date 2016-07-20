package services;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
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

        //return em.createQuery("SELECT * FROM items i", String.class).getResultList();

    }

    @Override
    @Transactional
    public Item addItem(Item item) {
        em.persist(item);
        //em.createNamedQuery("").setFlushMode(FlushModeType.COMMIT);
        return item;
    }

    @Override
    @Transactional
    public boolean deleteItemById(Long id) {
        // TODO Auto-generated method stub
        return false;
    }
}
