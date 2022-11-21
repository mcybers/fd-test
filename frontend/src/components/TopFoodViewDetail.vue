<template>

    <v-card outlined>
        <v-card-title>
            TopFood # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <String label="Foodid" v-model="item.foodid" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="Count" v-model="item.count" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'TopFoodViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/topFoods/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

