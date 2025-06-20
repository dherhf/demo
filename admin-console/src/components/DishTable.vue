<template>
  <div class="flex bg-gray-50 relative z-0" style="height: calc(100vh - 4rem)">
    <!-- 左侧：菜品分类列表 -->
    <div class="w-64 bg-white shadow-lg p-4 flex flex-col">
      <h3 class="text-xl font-semibold text-gray-800 mb-4">菜品分类</h3>
      <div v-if="loadingDishCategory">加载中...</div>
      <div v-else-if="dishCategory.length === 0">暂无分类数据</div>
      <ul v-else class="space-y-2 overflow-y-auto">
        <li
          v-for="category in dishCategory"
          :key="category.id"
          class="cursor-pointer p-3 rounded transition duration-200"
          :class="
            currentCategoryId === category.id
              ? 'bg-blue-50 border-l-4 border-blue-500'
              : 'hover:bg-gray-100'
          "
          @click="selectCategory(category.id)"
        >
          <div>{{ category.tags }}</div>
        </li>
      </ul>

      <!-- 添加分类按钮 -->
      <button
        @click="showAddCategoryModal = true"
        class="mt-4 bg-green-600 hover:bg-green-700 text-white font-medium py-2 px-4 rounded-lg transition duration-200 flex items-center justify-center"
      >
        <i class="fa fa-plus mr-2"></i> 添加分类
      </button>
    </div>

    <!-- 右侧：菜品内容区域 -->
    <div class="flex-1 p-6 overflow-auto">
      <!-- 单行表格：当前分类信息 -->
      <div class="mb-8 bg-white rounded-lg shadow-md overflow-hidden">
        <table class="w-full">
          <thead>
            <tr class="bg-gray-100">
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
              >
                分类ID
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
              >
                分类名称
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
              >
                描述
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="currentCategory">
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                {{ currentCategory.id }}
              </td>
              <td
                class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900"
              >
                {{ currentCategory.tags }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                {{ currentCategory.description }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- 主要区域：菜品表格 -->
      <div class="bg-white rounded-lg shadow-md overflow-hidden">
        <!-- 表格标题区域 -->
        <div
          class="px-6 py-4 border-b border-gray-200 flex justify-between items-center"
        >
          <h3 class="text-lg font-medium text-gray-900">
            {{ currentCategory ? currentCategory.tags : "请选择分类" }} 下的菜品
          </h3>
          <button
            @click="openDishModal(null)"
            class="bg-blue-600 hover:bg-blue-700 text-white text-sm px-4 py-2 rounded-md"
            :disabled="!currentCategory"
          >
            添加菜品
          </button>
        </div>

        <div class="overflow-x-auto">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th
                  scope="col"
                  class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  菜品ID
                </th>
                <th
                  scope="col"
                  class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  菜品名称
                </th>
                <th
                  scope="col"
                  class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  价格
                </th>
                <th
                  scope="col"
                  class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  描述
                </th>
                <th
                  class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                >
                  操作
                </th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr
                v-for="dish in filteredDishes"
                :key="dish.id"
                class="hover:bg-gray-50 transition duration-150"
              >
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                  {{ dish.id }}
                </td>
                <td
                  class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900"
                >
                  {{ dish.name }}
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                  ¥{{ dish.price }}
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                  {{ dish.description }}
                </td>
                <td
                  class="px-6 py-4 whitespace-nowrap text-sm text-gray-500 flex space-x-4"
                >
                  <button
                    @click="openDishModal(dish)"
                    class="text-blue-600 hover:underline hover:text-blue-800"
                  >
                    编辑
                  </button>
                  <button
                    @click="deleteDish(dish.id)"
                    class="text-red-600 hover:underline hover:text-red-800"
                  >
                    删除
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- 添加分类模态框 -->
    <div
      v-if="showAddCategoryModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-[1000]"
    >
      <div
        class="bg-white rounded-lg shadow-xl w-full max-w-md p-6 transform transition-all"
      >
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-lg font-medium text-gray-900">添加菜品分类</h3>
          <button
            @click="showAddCategoryModal = false"
            class="text-gray-400 hover:text-gray-500"
          >
            <i class="fa fa-times"></i>
          </button>
        </div>

        <div class="space-y-4">
          <div>
            <label
              for="categoryName"
              class="block text-sm font-medium text-gray-700 mb-1"
              >分类名称</label
            >
            <input
              type="text"
              id="categoryName"
              v-model="newCategory.tags"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              placeholder="请输入分类名称"
            />
          </div>

          <div>
            <label
              for="categoryDescription"
              class="block text-sm font-medium text-gray-700 mb-1"
              >分类描述</label
            >
            <textarea
              id="categoryDescription"
              v-model="newCategory.description"
              rows="3"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              placeholder="请输入分类描述"
            ></textarea>
          </div>

          <div class="flex justify-end space-x-3">
            <button
              @click="showAddCategoryModal = false"
              class="px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            >
              取消
            </button>
            <button
              @click="addCategory"
              class="px-4 py-2 bg-green-600 border border-transparent rounded-md shadow-sm text-sm font-medium text-white hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500"
            >
              确认添加
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 菜品模态框 -->
    <div
      v-if="showDishModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-[1000]"
    >
      <div
        class="bg-white rounded-lg shadow-xl w-full max-w-md p-6 transform transition-all"
      >
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-lg font-medium text-gray-900">
            {{ isEdit ? "编辑菜品" : "添加菜品" }}
          </h3>
          <button
            @click="showDishModal = false"
            class="text-gray-400 hover:text-gray-500"
          >
            <i class="fa fa-times"></i>
          </button>
        </div>

        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1"
              >菜品名称</label
            >
            <input
              v-model="currentDish.name"
              type="text"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="请输入菜品名称"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1"
              >价格</label
            >
            <input
              v-model.number="currentDish.price"
              type="number"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="请输入价格"
            />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1"
              >描述</label
            >
            <textarea
              v-model="currentDish.description"
              rows="3"
              class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="请输入描述"
            ></textarea>
          </div>

          <div class="flex justify-end space-x-3">
            <button
              @click="showDishModal = false"
              class="px-4 py-2 border border-gray-300 rounded-md text-sm text-gray-700 bg-white hover:bg-gray-50"
            >
              取消
            </button>
            <button
              @click="saveDish"
              class="px-4 py-2 bg-blue-600 text-white text-sm rounded-md hover:bg-blue-700"
            >
              {{ isEdit ? "保存" : "确认添加" }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from "vue";
import axios from "axios";

// 状态管理：加载状态和错误处理
const loadingDishCategory = ref(false);
const loadingDish = ref(false);
const error = ref(null);

const dishCategory = ref([]);

const fetchDishCategoryData = async () => {
  loadingDishCategory.value = true;
  error.value = null;
  try {
    const response = await axios.get(
      "http://localhost:8080/api/dish-category",
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    dishCategory.value = response.data || [];
  } catch (err) {
    error.value = err.message || "获取分类数据失败";
    console.error("获取分类数据失败:", err);
  } finally {
    loadingDishCategory.value = false;
  }
};

// 当前选中的分类ID
const currentCategoryId = ref(null);

// 添加分类模态框状态
const showAddCategoryModal = ref(false);

// 新分类数据
const newCategory = ref({
  tags: "",
  description: "",
});

// 选择分类的方法
const selectCategory = (id) => {
  currentCategoryId.value = id;
};

// 当前选中的分类对象
const currentCategory = computed(() =>
  dishCategory.value.find((category) => category.id === currentCategoryId.value)
);

// 添加分类的方法
const addCategory = async () => {
  try {
    // 发送数据到后端
    const response = await axios.post(
      "http://localhost:8080/api/dish-category",
      {
        tags: newCategory.value.tags,
        description: newCategory.value.description,
      }
    );

    // 更新本地数据
    dishCategory.value.push(response.data);

    // 关闭模态框并重置表单
    showAddCategoryModal.value = false;
    newCategory.value = {
      tags: "",
      description: "",
    };

    // 自动选择新添加的分类
    currentCategoryId.value = response.data.id;

    alert("分类添加成功！");
  } catch (err) {
    error.value = err.message || "添加分类失败";
    console.error("添加分类失败:", err);
    alert("添加分类失败，请重试");
  }
};

const dishes = ref([]);

const fetchDishData = async () => {
  loadingDish.value = true;
  error.value = null;
  try {
    const response = await axios.get("http://localhost:8080/api/dish", {
      headers: {
        "Content-Type": "application/json",
      },
    });
    dishes.value = response.data;
  } catch (err) {
    error.value = err.message || "获取菜品数据失败";
    console.error("获取菜品数据失败:", err);
  } finally {
    loadingDish.value = false;
  }
};

// 根据当前分类过滤的菜品列表
const filteredDishes = computed(() =>
  currentCategory.value
    ? dishes.value.filter(
        (dish) => dish.categoryId === currentCategory.value.id
      )
    : []
);

// 合并后的菜品模态框状态
const showDishModal = ref(false);
// 当前操作的菜品数据
const currentDish = ref({
  id: null,
  name: "",
  price: 0,
  description: "",
  categoryId: null,
});
// 是否为编辑操作
const isEdit = ref(false);

// 打开菜品模态框
const openDishModal = (dish) => {
  if (dish) {
    currentDish.value = { ...dish };
    isEdit.value = true;
  } else {
    currentDish.value = {
      id: null,
      name: "",
      price: 0,
      description: "",
      categoryId: currentCategory.value ? currentCategory.value.id : null,
    };
    isEdit.value = false;
  }
  showDishModal.value = true;
};

// 保存菜品信息
const saveDish = async () => {
  if (!currentCategory.value) {
    alert("请先选择分类");
    return;
  }
  if (!currentDish.value.name.trim()) {
    alert("菜品名称不能为空");
    return;
  }

  try {
    let response;
    if (isEdit.value) {
      response = await axios.put(
        `http://localhost:8080/api/dish/${currentDish.value.id}`,
        {
          id: currentDish.value.id,
          name: currentDish.value.name,
          price: currentDish.value.price,
          description: currentDish.value.description,
          categoryId: currentCategory.value.id,
        }
      );
      const dishIndex = dishes.value.findIndex(
        (d) => d.id === currentDish.value.id
      );
      if (dishIndex !== -1) {
        dishes.value[dishIndex] = response.data;
      }
    } else {
      response = await axios.post("http://localhost:8080/api/dish", {
        name: currentDish.value.name,
        price: currentDish.value.price,
        description: currentDish.value.description,
        categoryId: currentCategory.value.id,
      });
      dishes.value.push(response.data);
    }

    showDishModal.value = false;
    alert(isEdit.value ? "菜品编辑成功！" : "菜品添加成功！");
  } catch (err) {
    error.value =
      err.message || (isEdit.value ? "编辑菜品失败" : "添加菜品失败");
    console.error(isEdit.value ? "编辑菜品失败:" : "添加菜品失败:", err);
    alert(isEdit.value ? "编辑菜品失败，请重试" : "添加菜品失败，请重试");
  }
};

// 删除菜品方法
const deleteDish = async (id) => {
  const confirmed = confirm("确定要删除这道菜品吗？");
  if (!confirmed) return;

  // 发送删除请求到服务器
  await axios.delete(`http://localhost:8080/api/dish/${id}`, {
    headers: {
      "Content-Type": "application/json",
    },
  });

  // 从本地列表中移除
  const index = dishes.value.findIndex((c) => c.id === id);
  if (index !== -1) {
    dishes.value.splice(index, 1);
  }
};

onMounted(fetchDishCategoryData);
onMounted(fetchDishData);
</script>
