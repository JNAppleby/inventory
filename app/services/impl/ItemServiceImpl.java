package services.impl;

import services.ItemService;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import entities.Item;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

@Named
public class ItemServiceImpl implements ItemService {

    private static final Logger log = LoggerFactory.getLogger(ItemService.class);

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
        if (item == null) {
            log.warn("Item to add is null");
            return null;
        }

        Query q = em.createQuery("SELECT COUNT(i) FROM Item i WHERE i.name = :name");
        q.setParameter("name", item.getName());

        Long dupCount = (Long)q.getSingleResult();

        if(dupCount > 0){
            log.info("Item {} already exists, not adding.", item.getId());
            return null;
        }

        em.persist(item);
        log.info("Added item id={}", item.getId());
        return item;
    }

    @Override
    @Transactional
    public void removeItemById(Long id) {
        Item item = em.find(Item.class, id);
        if (item == null) {
            log.warn("Cannot removing item. Non-existent item with id={}", id);
        } else {
            em.remove(item);
            log.info("Removed item id={}", id);
        }
    }
}
