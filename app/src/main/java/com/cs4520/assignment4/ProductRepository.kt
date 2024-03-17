package com.cs4520.assignment4

class ProductRepository(private val productApi: ProductApi, private val productDao: ProductDao) {
    suspend fun getProducts(page: Int? = null): List<Product> {
        return try {
            println("MyApp: before API")
            val products = productApi.getProducts() //page
            println("MyApp: products:" + products)
            val distinctProducts = products.distinctBy { it.name }
            val filteredProducts = distinctProducts.filter { product ->
                product.name.isNotBlank() && product.type.isNotBlank() && product.price >= 0
            }

            val productEntities = filteredProducts.map { product ->
                ProductEntity(product.name, product.type, product.expiryDate, product.price)
            }
            println("MyApp: product entities:" + productEntities)
            productDao.insertProducts(productEntities)

            println("MyApp: got to the spot")
            filteredProducts
        } catch (e: Exception) {
            println("MyApp: Error inserting products into the database: ${e.message}")
            e.printStackTrace()
            val cachedProducts = productDao.getAllProducts()
            cachedProducts.map { productEntity ->
                Product(productEntity.name, productEntity.type, productEntity.expiryDate, productEntity.price)
            }
        }
    }
}