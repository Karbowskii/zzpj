<template>
    <div>
        <b-modal id="betting-modal" centered title="Make your bet!" :hide-footer=true>
            <form @submit="onSubmit" @submit.prevent>
                <div class="bet">
                    <div class="text-center stake">Stake is x{{betStake.toFixed(2)}}</div>
                    <label for="bet-input" class="text-center">Your bet:</label>
                </div>
                <b-form-input :min="1" :max="$store.state.user.coins" id="bet-input" v-model="betValue" type="number">
                </b-form-input>
                <b-button type="submit">Make a bet!</b-button>
            </form>
        </b-modal>
    </div>
</template>

<script>

    import {betService} from "../App";

    export default {
        name: "BettingModal",
        props: ['betStake', 'isASelected', 'matchId'],
        data: function () {
            return {
                betValue: 0,
            }
        },
        methods: {
            onSubmit() {
                betService.createBet({
                    selectedA: this.isASelected,
                    matchId: this.matchId,
                    coins: this.betValue
                }).then((response) => {
                    if (response.errors) {
                        this.$bvToast.toast(response.errors.message, {
                            title: 'Error',
                            variant: 'danger',
                            toaster: 'b-toaster-top-center'
                        });
                    } else {
                        this.$store.state.user.coins -= this.betValue;
                        this.betValue = 0;
                        this.$bvToast.toast('Your bet was created!', {
                            title: 'Bet',
                            variant: 'success',
                            toaster: 'b-toaster-top-center'
                        });
                    }
                    this.$bvModal.hide('betting-modal');
                });
            }
        }
    }
</script>

<style scoped>

    .stake {
        font-size: 20px;
        color: var(--colour4);
    }

    button {
        float: right;
        margin-top: 13px;
        margin-right: 5px;
        border: 2px solid var(--colour4);
        background: none;
        color: #b9b9b9;
    }

    button:hover {
        border: 2px solid var(--colour4);
        background: none;
        text-shadow: 0 0 5px var(--colour5);
    }

    button:active, button:focus {
        background: none!important;
        border: 2px solid var(--colour4)!important;
        box-shadow: none!important;
    }

    label {
        color: var(--colour4);
    }
</style>
