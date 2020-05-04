<template>
    <div>
        <b-modal id="betting-modal" centered title="Make your bet!">
            <form>
                <b-form-group>
                    <b-form-input>

                    </b-form-input>
                </b-form-group>
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
                betValue: null,
            }
        }
    }
</script>

<style scoped>

</style>