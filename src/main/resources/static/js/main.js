
Vue.component('products-list', {
    props: ['products'],
    template: '<div>' +
        '<div v-for="product in products" >{{product.name}}</div>' +
        '</div>'
});

var app = new Vue({
    el: '#app',
    template: '<products-list :products="products" />',
    data: {
        products: [
            { id: 1, name: 'Яблоки Гренни', price: 100 },
            { id: 2, name: 'Бананы', price: 80 },
            { id: 3, name: 'Хайнз Кетчуп Чеснок', price: 120 },
        ]
    }
});