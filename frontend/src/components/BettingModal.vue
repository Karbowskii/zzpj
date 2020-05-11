<template>
    <div>
        <b-modal id="betting-modal" centered title="Make your bet!" hide-footer="true">
            <form @submit="onSubmit" @submit.prevent>
                <div class="bet">
                    <div class="text-center stake">Stake is x{{betStake.toFixed(2)}}</div>
                    <label  for="bet-input" class="text-center">Your bet:</label>
                </div>
                <b-form-input :min="1" :max="100" id="bet-input" v-model="betValue" type="number">
                </b-form-input>
                <b-button type="submit">Make a bet!</b-button>
            </form>
        </b-modal>
    </div>
</template>

<script>
    export default {
        name: "BettingModal",
        props:['betStake'],
        data: function () {
            return{
                betValue: 0,
            }
        },
        methods: {
          onSubmit(){
              this.betValue = null;
              this.$bvModal.hide('betting-modal');
              /* TODO Zapisywanko zakladu do DB*/
          }
        }
    }
</script>

<style scoped>

    .stake{
        font-size: 20px;
        color: var(--colour4);
    }

    button{
        float: right;
        margin-top: 13px;
        margin-right: 5px;
        border: 2px solid var(--colour4);
        background: none;
        color: #b9b9b9;
    }

    button:hover{
        border: 2px solid var(--colour4);
        background: none;
        text-shadow: 0 0 5px var(--colour5);
    }

    label{
        color: var(--colour4);
    }
</style>