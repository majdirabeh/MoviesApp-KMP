package fr.majdi.moviesapp.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.defaultModule
/**
 * Init Koin library
 * @param appDeclaration - KoinAppDeclaration
 * @return KoinApplication
 * @author Majdi
 */
fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    //We can use generated modules by KSP
    //modules(defaultModule)
    //We can use manuel declared modules
    modules(appModule)
}