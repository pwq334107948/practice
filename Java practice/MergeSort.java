public class MergeSort {
    // 采用分割指针的方式进行合并排序，仅新建一个临时数列存数即可完成对原始列的排序。资源占用低。
    
    void mergeSort(int[] unsorted) {
        int[] temp = new int[unsorted.length]; //新建临时数列用于储存，避免每次分割都要新建数列；
        splitThenMerge(unsorted,temp,0,unsorted.length);
    }

    void splitThenMerge(int[] array,int[] temp, int start, int end) {
       while(start < end){ //基底条件
           int middleIndex = (start + end)/2; //对称分割原数列指针
           splitThenMerge(array, temp, start, middleIndex); //递归继续分割左边数列指针
           splitThenMerge(array, temp, middleIndex, end); //递归继续分割右边数列指针
           sortThenMerge(array, temp, start, middleIndex, end); //将分割的两个数列进行排序并合并
       } 
    }

    void sortThenMerge(int[] array, int[] temp, int start, int middleIndex, int end){
        int tempIndex = start; //临时数列指针
        int leftIndex = start; //坐标数列开始指针
        int rightIndex = middleIndex; //右边数列开始指针
        while(leftIndex < middleIndex && rightIndex < end){ //如果左边数列和右边数列都有数，则比较两个数列值的大小
            temp[tempIndex++] = array[leftIndex] < array[rightIndex] ? array[leftIndex++] : array[rightIndex++];
        } //经过以上循环，此时肯定有一个数列已经到头
        while(leftIndex < middleIndex) temp[tempIndex++] = array[leftIndex++]; //当左边数列没到头，则继续把左边数列的数填进临时数列
        while(rightIndex < end) temp[tempIndex++] = array[rightIndex++]; //当右边数列没到头，则继续把右边数列的数填进临时数列
        for(int a = start; a < end; a++){ //将排序后的临时数列重新赋值给原数列，对原数列进行排序
            array[a] = tempIndex[a];
        }
    }

    @test
    public void mergeSortTest() {
        var unsorted = new int[]{5, 6, 16, 13, 2, 3, 10, 2, 3};
        var sorted = new int[]{2, 2, 3, 3, 5, 6, 10, 13, 16};
        Assertions.assertArrayEquals(sorted, mergeSort(unsorted));

}