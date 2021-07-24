object Data {
  def normalize(data: List[Float]): List[Float] =
    val min = data.min
    val max = data.max
    return data.map(d => (d - min) / (max - min))
    
    
}
