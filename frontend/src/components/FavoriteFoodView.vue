<template>

    <v-data-table
        :headers="headers"
        :items="favoriteFood"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'FavoriteFoodView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "foodId", value: "foodId" },
                { text: "count", value: "count" },
            ],
            favoriteFood : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/favoriteFoods'))

            temp.data._embedded.favoriteFoods.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.favoriteFood = temp.data._embedded.favoriteFoods;
        },
        methods: {
        }
    }
</script>

