<template>
    <div>
        <b-container>
            <b-row align-h="center">
                <b-col cols="5">
                    <div class="login-panel">
                        <b-form class="text-left" @submit.prevent="logIn">
                            <label for="login-input">Username:</label>
                            <b-form-input id="login-input" type="text" required v-model="login">
                            </b-form-input>
                            <label for="password-input">Password:</label>
                            <b-form-input id="password-input" type="password" required v-model="password">
                            </b-form-input>
                            <!--                            <div class="remind-password">-->
                            <!--                                <b-link :to="{path:'/Home'}" class="profile">Forgot a password?</b-link>-->
                            <!--                            </div>-->
                            <b-button type="submit">Sign in</b-button>
                            <b-button @click="$router.replace({path:'/create-account'})">New Account</b-button>
                        </b-form>
                    </div>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>

    import {authorizationService} from "../App";
    import LevelToIconMapper from "../Core/LevelToIconMapper";

    export default {
        name: "LoginView",
        data: function () {
            return {
                login: '',
                password: '',
            }
        },
        methods: {
            logIn() {
                authorizationService.login(this.login, this.password)
                    .then((response) => {
                            if (response.errors) {
                                this.$bvToast.toast('Wrong username or password!',{
                                    title: 'Login error',
                                    variant: 'danger',
                                    toaster: 'b-toaster-top-center'
                                });
                                this.password = '';
                            } else {
                                response.user.icon = LevelToIconMapper.getUrl(response.user.level.id);
                                this.$store.commit('login', {user: response.user, token: response.token});
                                this.$router.push({path: `/`});
                            }
                        }
                    )
            }
        },
        mounted() {

            if (this.$route.query.newAccount) {
                this.$bvToast.toast('Your account was created!', {
                    title: 'Account created',
                    variant: 'success',
                    toaster: 'b-toaster-top-center'
                });
            }
        }
    }
</script>

<style scoped>

    label {
        color: white;
        font-size: 20px;
    }

    input {
        margin-bottom: 25px;
    }

    button {
        float: right;
        margin-left: 10px;
        margin-top: 30px;
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
        background: none !important;
        border: 2px solid var(--colour4) !important;
        box-shadow: none !important;
    }

    .remind-password {
        margin-top: -14px;
    }

    .remind-password a {
        color: #78797a;
    }

    .remind-password a:hover {
        text-decoration: none;
        color: var(--colour4);
    }

    .login-panel {
        margin-top: 130px;
        background: var(--colour2);
        box-shadow: 0 0 13px 9px var(--colour4);
        padding: 30px 30px 80px 30px;
    }

    .err, .err:hover {
        color: #e21f25;
    }

</style>
