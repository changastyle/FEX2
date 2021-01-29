package dao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DAOEclipse
{
    private static final String PERSISTENCE_UNIT_NAME = "FEX2PU";
    
    public static List findAllByJPQL(String jpql)
    {
        EntityManagerFactory factory;
        List lista = new ArrayList();
       
        long timestampIncial = System.currentTimeMillis();
        
        //PIDO UN ENTITYMANAGER A LA FACTORIA:
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
       

        //INICIO UNA TRANSACCION (?
        em.getTransaction().begin();

        //CREO UNA QUERY JPQL:
        Query query = em.createQuery(jpql);
        em.flush();

        //CIERRO LA TRANSACCION:
        em.getTransaction().commit();
        
        long timestampfinal = System.currentTimeMillis();
        
        //POPULO LISTA:
        lista = query.getResultList();
        em.close();
        factory.close();
        //MENSAJE POR CONSOLA:
        System.out.println("Query(\"" + jpql +"\") tiene " + lista.size() +" resultados en " + (timestampfinal - timestampIncial) + " ms.");
        
        return lista;
    }
    public static List findAllByJPQLLimitado(String jpql,  int offset , int limite)
    {
        EntityManagerFactory factory;
        List lista = new ArrayList();
       
        long timestampIncial = System.currentTimeMillis();
        
        //PIDO UN ENTITYMANAGER A LA FACTORIA:
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
       

        //INICIO UNA TRANSACCION (?
        em.getTransaction().begin();

        //CREO UNA QUERY JPQL:
        Query query = em.createQuery(jpql);
        query.setFirstResult(offset);
        query.setMaxResults(limite);
        em.flush();

        //CIERRO LA TRANSACCION:
        em.getTransaction().commit();
        
        long timestampfinal = System.currentTimeMillis();
        
        //POPULO LISTA:
        lista = query.getResultList();
        em.close();
        factory.close();
        //MENSAJE POR CONSOLA:
        System.out.println("Query(\"" + jpql +"\") tiene " + lista.size() +" resultados en " + (timestampfinal - timestampIncial) + " ms.");
        
        return lista;
    }
    public static Object get(Class clase , int id)
    {
        EntityManagerFactory factory;
        Object result;
       
        long timestampIncial = System.currentTimeMillis();
        
        //PIDO UN ENTITYMANAGER A LA FACTORIA:
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        //INICIO UNA TRANSACCION (?
        //em.getTransaction().begin();

        //CREO UNA QUERY JPQL:
        result = em.find(clase, id);
        em.close();
         factory.close();
        //CIERRO LA TRANSACCION:
        //em.getTransaction().commit();

        long timestampfinal = System.currentTimeMillis();
        
        //MENSAJE POR CONSOLA:
        System.out.println("GET(\"" + clase.getSimpleName() +" | " + id +"\") tiene " + result +" resultados en " + (timestampfinal - timestampIncial) + " ms.");
        
        return result;
    }
//    public static Object get(Class clase , long id)
//    {
//        EntityManagerFactory factory;
//        Object result;
//       
//        long timestampIncial = System.currentTimeMillis();
//        
//        //PIDO UN ENTITYMANAGER A LA FACTORIA:
//        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//        EntityManager em = factory.createEntityManager();
//
//        //INICIO UNA TRANSACCION (?
//        //em.getTransaction().begin();
//
//        //CREO UNA QUERY JPQL:
//        result = em.find(clase, id);
//        em.close();
//         factory.close();
//        //CIERRO LA TRANSACCION:
//        //em.getTransaction().commit();
//
//        long timestampfinal = System.currentTimeMillis();
//        
//        //MENSAJE POR CONSOLA:
//        System.out.println("GET(\"" + clase.getSimpleName() +" | " + id +"\") tiene " + result +" resultados en " + (timestampfinal - timestampIncial) + " ms.");
//        
//        return result;
//    }
    public static boolean save(Object nuevo)
    {
        boolean agregado = false;
        
        EntityManagerFactory factory;
       
        long timestampIncial = System.currentTimeMillis();
        
        //PIDO UN ENTITYMANAGER A LA FACTORIA:
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        
        //INICIO UNA TRANSACCION (?
        em.getTransaction().begin();
        
        //GUARDO EL OBJETO NUEVO:
        em.persist(nuevo);
        
        //CIERRO LA TRANSACCION:
        em.getTransaction().commit();
       em.close();
        factory.close();
        agregado = true;
        
        long timestampfinal = System.currentTimeMillis();
        
        //MENSAJE POR CONSOLA:
        System.out.println("save(\"" + nuevo +"\") en " + (timestampfinal - timestampIncial) + " ms.");
        

        return agregado;
    }
    public static boolean update(Object objConModificaciones)
    {
        boolean modificado = false;
        
        EntityManagerFactory factory;
       
        long timestampIncial = System.currentTimeMillis();
        
        //PIDO UN ENTITYMANAGER A LA FACTORIA:
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        
        //INICIO UNA TRANSACCION (?
        em.getTransaction().begin();
        
        //GUARDO EL OBJETO NUEVO:
        em.merge(objConModificaciones);
        
        //CIERRO LA TRANSACCION:
        em.getTransaction().commit();
        
        em.close();
        factory.close();
        modificado = true;
        
        long timestampfinal = System.currentTimeMillis();
        
        //MENSAJE POR CONSOLA:
        System.out.println("update(\"" + objConModificaciones +"\") en " + (timestampfinal - timestampIncial) + " ms.");
        
        return modificado;
    }
    public static Object updateReturnObj(Object objConModificaciones)
    {
        
        EntityManagerFactory factory;
       
        long timestampIncial = System.currentTimeMillis();
        
        //PIDO UN ENTITYMANAGER A LA FACTORIA:
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        
        //INICIO UNA TRANSACCION (?
        em.getTransaction().begin();
        
        //GUARDO EL OBJETO NUEVO:
        objConModificaciones = em.merge(objConModificaciones);
        
        //CIERRO LA TRANSACCION:
        em.getTransaction().commit();
        
        em.close();
        factory.close();
        
        long timestampfinal = System.currentTimeMillis();
        
        //MENSAJE POR CONSOLA:
        System.out.println("update(\"" + objConModificaciones +"\") en " + (timestampfinal - timestampIncial) + " ms.");
        
        return objConModificaciones;
    }
    public static boolean remove(Object objToRemove)
    {
        boolean quitado = false;
        
        EntityManagerFactory factory;
       
        long timestampIncial = System.currentTimeMillis();
        
        //PIDO UN ENTITYMANAGER A LA FACTORIA:
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        
        //INICIO UNA TRANSACCION (?
        em.getTransaction().begin();
        
        //GUARDO EL OBJETO NUEVO:
        Object objToRemovePosta = em.merge(objToRemove);
        em.remove(objToRemovePosta);
        
        //CIERRO LA TRANSACCION:
        em.getTransaction().commit();
        em.close();
        factory.close();
        quitado = true;
        
        long timestampfinal = System.currentTimeMillis();
        
        //MENSAJE POR CONSOLA:
        System.out.println("remove(\"" + objToRemove +"\") en " + (timestampfinal - timestampIncial) + " ms.");
        

        return quitado;
    }
    public static Object getByJPQL(String jpql)
    {
        EntityManagerFactory factory;
        List lista = new ArrayList();
       
        long timestampIncial = System.currentTimeMillis();
        
        //PIDO UN ENTITYMANAGER A LA FACTORIA:
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
       

        //INICIO UNA TRANSACCION (?
        em.getTransaction().begin();

        //CREO UNA QUERY JPQL:
        Query query = em.createQuery(jpql);
        query.setMaxResults(1);
        em.flush();

        //CIERRO LA TRANSACCION:
        em.getTransaction().commit();
        
        long timestampfinal = System.currentTimeMillis();
        
        //POPULO LISTA:
        lista = query.getResultList();
        em.close();
        factory.close();
        //MENSAJE POR CONSOLA:
        System.out.println("Query(\"" + jpql +"\") tiene " + lista.size() +" resultados en " + (timestampfinal - timestampIncial) + " ms.");
        
        Object objetoResultado = null;
        if(lista != null)
        {
            if(lista.size() > 0)
            {
                objetoResultado = lista.get(0);
            }
        }
        return objetoResultado;
    }
}