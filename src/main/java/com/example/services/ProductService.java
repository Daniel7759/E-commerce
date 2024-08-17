package com.example.services;

import com.example.exceptions.ResourceNotFoundException;
import com.example.models.MarcaEntity;
import com.example.models.ProductEntity;
import com.example.models.SubcategoryEntity;
import com.example.repositories.MarcaRepository;
import com.example.repositories.ProductRepository;
import com.example.repositories.SubcategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService extends GenericServicesImpl<ProductEntity, Long, ProductRepository>{

    @PersistenceContext
    private EntityManager entityManager;

    private final ImageProductService imageProductService;
    private final MarcaRepository marcaRepository;
    private final SubcategoryRepository subcategoryRepository;

    public ProductService(ProductRepository repository, ImageProductService imageProductService, MarcaRepository marcaRepository, SubcategoryRepository subcategoryRepository) {
        super(repository);
        this.imageProductService = imageProductService;
        this.marcaRepository = marcaRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @Transactional
    public ProductEntity insert(String productName, String description, Long price, Long stock, Long marcaId, Long subcategoryId, List<MultipartFile> imagenes) throws Exception {
        ProductEntity existingProductName = repository.findByName(productName).orElse(null);
        if (existingProductName != null) {
            throw new Exception("Product with name "+productName+" already exists");
        }
        ProductEntity newProduct = new ProductEntity();
        newProduct.setSku("SKU"+(repository.count()+1));
        newProduct.setName(productName);
        newProduct.setDescription(description);
        newProduct.setPrice(price);
        newProduct.setStock(stock);
        newProduct.setCreatedAt(LocalDate.now());
        MarcaEntity marca = marcaRepository.findById(marcaId)
                .orElseThrow(()-> new ResourceNotFoundException(MarcaEntity.class.getSimpleName()+" not found with id "+marcaId));
        newProduct.setMarca(marca);
        SubcategoryEntity subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(()-> new ResourceNotFoundException(SubcategoryEntity.class.getSimpleName()+" not found with id "+subcategoryId));
        newProduct.setSubcategory(subcategory);
        repository.save(newProduct);

        for (MultipartFile imagen:imagenes){
            imageProductService.saveImageProduct(imagen, newProduct.getProductId());
        }

        return newProduct;
    }

    @Transactional
    public ProductEntity findById(Long id) {
        ProductEntity product = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(ProductEntity.class.getSimpleName()+" not found with id "+id));
        if (product.getViews() == null) {
            product.setViews(1L);
        } else {
            product.setViews(product.getViews() + 1);
        }
        return repository.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> finTop5Views(){
        return repository.findTop5ByViewsNotNullOrderByViewsDesc(PageRequest.of(0, 5));
    }

    @Transactional(readOnly = true)
    public ProductEntity findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(()-> new ResourceNotFoundException(ProductEntity.class.getSimpleName()+" not found with name "+name));
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> findBySubcategoryId(Long subcategoryId) {
        return repository.findBySubcategorySubcategoryId(subcategoryId);
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> findByMarcaId(Long marcaId) {
        return repository.findByMarcaMarcaId(marcaId);
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> findBySubcategoryName(String subcategoryName) {
        return repository.findBySubcategoryName(subcategoryName);
    }

    //Metodo para filtrar con Criteria API
    public List<ProductEntity> filterProducts(List<String> subcategoryNames, List<String> marcaNames, Double minPrice, Double maxPrice) {
        // Inicializa la consulta
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> cq = cb.createQuery(ProductEntity.class);

        // Define la raíz de la consulta
        Root<ProductEntity> product = cq.from(ProductEntity.class);

        // Crea una lista para almacenar las condiciones de la consulta
        List<Predicate> predicates = new ArrayList<>();

        // Agrega condiciones en función de los parámetros presentes
        if (subcategoryNames != null && !subcategoryNames.isEmpty()) {
            predicates.add(product.get("subcategory").get("name").in(subcategoryNames));
        }
        if (marcaNames != null && !marcaNames.isEmpty()) {
            predicates.add(product.get("marca").get("name").in(marcaNames));
        }
        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(product.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(product.get("price"), maxPrice));
        }

        // Aplica las condiciones a la consulta
        cq.where(predicates.toArray(new Predicate[0]));

        // Ordena los resultados por Id de orden ascendente
        cq.orderBy(cb.asc(product.get("productId")));

        // Ejecuta la consulta y devuelve los resultados
        return entityManager.createQuery(cq).getResultList();
    }
}
