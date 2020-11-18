package robin.vitalij.fortniteassitant.utils.mapper.base

interface Mapper<T, R> {

    fun transform(obj: T): R

}