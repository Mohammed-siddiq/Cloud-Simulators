package com.hw1.utils

import java.util

import scala.collection.mutable.ListBuffer

/**
  * Util to perform data type conversion . ex : Converts scala list to JAVA lists and vice-versa
  *
  */
class ConversionUtil {

  def toJList[T](l: List[T]): util.List[T] = {
    val a = new util.ArrayList[T]
    l.map(a.add(_))
    a
  }

  def toSList[T](l: util.List[T]): List[T] = {
    var a = ListBuffer[T]()
    for (r <- 0 until l.size) a += l.get(r)
    a.toList
  }

}
