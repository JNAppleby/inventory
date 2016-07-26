package services.impl;

import services.ItemService;

import errcodes.ErrCode;

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

    @PersistenceContext private EntityManager em;

    @Override
    public List<Item> getAllItems() {
        log.debug("getAllItems() is called");
        CriteriaQuery<Item> c = em.getCriteriaBuilder().createQuery(Item.class);
        c.from(Item.class);
        return em.createQuery(c).getResultList();
    }

    @Override
    @Transactional
    public ErrCode addItem(Item item) {
        if (item == null) {
            log.warn("item is null in addItem()");
            return ErrCode.ADD_NULL_ITEM;
        }

        Long dupCount = (Long)em.createQuery("SELECT COUNT(i) FROM Item i WHERE i.name = :name")
                        .setParameter("name", item.getName())
                        .getSingleResult();

        if (dupCount > 0) {
            log.warn("Duplicate item detected when adding, item id={}", item.getId());
            return ErrCode.ADD_DUPLICATE_ITEM;
        }

        em.persist(item);
        log.info("Added item id={}", item.getId());
        return ErrCode.ADD_SUCCESS;
    }

    @Override
    @Transactional
    public ErrCode removeItemById(Long id) {
        Item item = em.find(Item.class, id);
        if (item == null) {
            log.warn("Cannot remove item. Non-existent item with id={}", id);
            return ErrCode.REMOVE_NON_EXISTENT;
        } else {
            em.remove(item);
            log.info("Removed item id={}", id);
            return ErrCode.REMOVE_SUCCESS;
        }
    }
}
