import { createRouter, createWebHistory } from "vue-router";
import LoginView from "@/views/LoginView.vue";
import DashboardView from "@/views/DashboardView.vue";
import CustomerTableView from "@/views/CustomerTableView.vue";
import DishTableView from "@/views/DishTableView.vue";
import DishOrderView from "@/views/DishOrderView.vue";
import EmployeeTableView from "@/views/EmployeeTableView.vue";
import DeskTableView from "@/views/DeskTableView.vue";
import HistoricalOrdersView from "@/views/HistoricalOrdersView.vue";
import OrderDetailsView from "@/views/OrderDetailsView.vue";
const routes = [
  {
    path: "/login",
    name: "Login",
    component: LoginView,
  },
  {
    path: "/dashboard",
    name: "Dashboard",
    component: DashboardView,
  },
  {
    path: "/customers",
    name: "CustomerTable",
    component: CustomerTableView,
  },
  {
    path: "/dish",
    name: "DishTable",
    component: DishTableView,
  },
  {
    path: "/order",
    name: "DishOrder",
    component: DishOrderView,
  },
  {
    path: "/employee",
    name: "EmployeeTable",
    component: EmployeeTableView,
  },
  {
    path: "/desk",
    name: "deskTable",
    component: DeskTableView,
  },
  {
    path: "/historical-orders",
    name: "HistoricalOrders",
    component: HistoricalOrdersView,
  },
  {
    path: "/order-details/:orderId",
    name: "OrderDetails",
    component: OrderDetailsView,
  },
  {
    path: "/",
    redirect: "/login",
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
