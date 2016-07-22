package services.impl;

import services.ItemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import entities.Item;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        em.persist(item);
        log.info("Added item id="+item.getId());
        return item;
    }

    @Override
    @Transactional
    public void deleteItemById(Long id) {
        Item item = em.find(Item.class, id);
        if(item == null){
            log.error("Error removing item. Non-existent item with id="+id);
        }else{
            em.remove(item);
            log.info("Removed item id="+id);
        }
    }
}
