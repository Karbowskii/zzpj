<template>
    <div>
        <b-container>
            <b-row align-h="center">
                <b-col cols="5">
                    <div class="login-panel">
                        <b-form class="text-left" @submit="logIn">
                            <label for="login-input">Login:</label>
                            <b-form-input id="login-input" type="text" required v-model="login">
                            </b-form-input>
                            <label for="password-input">Password:</label>
                            <b-form-input id="password-input" type="password" required v-model="password">
                            </b-form-input>
                            <div class="remind-password"><b-link :to="{path:'/Home'}" class="profile">Forgot a password?</b-link></div>
                            <b-button type="submit">Sign in</b-button>
                            <b-button type="submit">New Account</b-button>
                        </b-form>
                    </div>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
    export default {
        name: "LoginView",
        data: function(){
            return{
                login: null,
                password: null
            }
        },
        methods:{
            logIn(){
                this.$router.push({ path: `/Home`})
            }
        }
    }
</script>

<style scoped>

    label{
        color: white;
        font-size: 20px;
    }

    input{
        margin-bottom: 25px;
    }

    button{
        float: right;
        margin-left: 10px;
        margin-top: 20px;
        border: 2px solid var(--colour4);
        background: none;
        color: #b9b9b9;
    }

    button:hover{
        border: 2px solid var(--colour4);
        background: none;
        /*color: var(--colour5);*/
        text-shadow: 0 0 5px var(--colour5);
    }

    .remind-password{
        margin-top: -14px;
    }

    .remind-password a{
        color: #78797a;
    }

    .remind-password a:hover{
        text-decoration: none;
        color: var(--colour4);
    }

    .login-panel{
        margin-top: 130px;
        background: var(--colour2);
        box-shadow: 0px 0px 13px 9px var(--colour4);
        padding: 30px 30px 80px 30px;
    }


</style>