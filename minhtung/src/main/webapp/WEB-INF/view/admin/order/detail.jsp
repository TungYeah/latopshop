<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <meta name="description" content="Dự án laptopshop" />
                    <meta name="author" content="minhtung2108" />
                    <title>Detail User</title>
                    <link href="/css/styles.css" rel="stylesheet" />

                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                </head>

                <body class="sb-nav-fixed">
                    <jsp:include page="../layout/header.jsp" />
                    <div id="layoutSidenav">
                        <jsp:include page="../layout/sidebar.jsp" />
                        <div id="layoutSidenav_content">
                            <main>
                                <div class="container-fluid px-4">
                                    <h1 class="mt-4">Manage Users</h1>
                                    <ol class="breadcrumb mb-4">
                                        <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                        <li class="breadcrumb-item"><a href="/admin/order">Orders</a></li>
                                    </ol>
                                    <div class="container mt-5">
                                        <div class="row">
                                            <div class="col-12 mx-auto">
                                                <div class="d-flex justify-content-between">
                                                    <h3>Order detail with id = ${id}</h3>
                                                </div>

                                                <hr />

                                                <table class=" table table-bordered table-hover">
                                                    <thead>
                                                        <tr>
                                                            <th>Product</th>
                                                            <th>Product Name</th>
                                                            <th>Price</th>
                                                            <th>Quantity</th>
                                                            <th>Total Price</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="orderDetail" items="${orderDetails}">

                                                            <tr>
                                                                <th scope="row">
                                                                    <div class="d-flex align-items-center">
                                                                        <img src="/images/product/${orderDetail.product.image}"
                                                                            class="img-fluid me-5 rounded-circle"
                                                                            style="width: 80px; height: 80px;" alt="">
                                                                    </div>
                                                                </th>
                                                                <td>
                                                                    ${orderDetail.product.name}
                                                                </td>
                                                                <td>
                                                                    <fmt:formatNumber
                                                                        value="${orderDetail.product.price}"
                                                                        pattern="#,##0" /> đ
                                                                </td>
                                                                <td>${orderDetail.quantity}</td>
                                                                <td>
                                                                    <fmt:formatNumber value="${orderDetail.price}"
                                                                        pattern="#,##0" /> đ
                                                                </td>
                                                                <td>
                                                                    <a href="/admin/order/${order.id}"
                                                                        class="btn btn-success">View</a>
                                                                    <a href="/admin/order/update/${order.id}"
                                                                        class="btn btn-warning  mx-2">Update</a>
                                                                    <a href="/admin/order/delete/${order.id}"
                                                                        class="btn btn-danger">Delete</a>
                                                                </td>
                                                            </tr>

                                                        </c:forEach>

                                                    </tbody>
                                                </table>
                                                <a href="/admin/order" class="btn btn-success mt-3">Back</a>

                                            </div>

                                        </div>

                                    </div>
                                </div>
                            </main>
                            <jsp:include page="../layout/footer.jsp" />
                        </div>
                    </div>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                        crossorigin="anonymous"></script>
                    <script src="/js/scripts.js"></script>

                </body>

                </html>