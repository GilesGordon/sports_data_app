package com.cs4520.assignment4

class ProductRepository(private val productApi: ProductApi, private val productDao: ProductDao) {
    suspend fun getProducts(page: Int? = null): List<Product> {
        return try {
            println("MyApp: before API")
            val products = productApi.getProducts() //page
            println("MyApp: products:" + products)
            val productEntities = products.map { product ->
                ProductEntity(product.name, product.type, product.expiryDate, product.price)
            }
            productDao.insertProducts(productEntities)
            products
        } catch (e: Exception) {
            val cachedProducts = productDao.getAllProducts()
            cachedProducts.map { productEntity ->
                Product(productEntity.name, productEntity.type, productEntity.expiryDate, productEntity.price)
            }
        }
    }
}