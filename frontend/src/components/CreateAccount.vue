<template>
    <div class="create-account">
        <b-container>
            <b-row align-h="center">
                <b-col cols="6">
                    <div class="create-account-panel">
                        <b-form class="text-left" @submit.prevent="createAccount">
                            <label for="email-input">Email:</label>
                            <b-form-input id="email-input" required type="email" v-model="email"></b-form-input>
                            <label for="login-input">Login:</label>
                            <b-form-input id="login-input" type="text" required v-model="login">
                            </b-form-input>
                            <label for="password-input">Password:</label>
                            <b-form-input id="password-input" type="password" required v-model="password">
                            </b-form-input>
                            <label for="password-confirm-input">Confirm password:</label>
                            <b-form-input id="password-confirm-input" type="password" required
                                          v-model="confirmPassword">
                            </b-form-input>
                            <label for="first-name-input">First name:</label>
                            <b-form-input id="first-name-input" type="text" required v-model="firstName"></b-form-input>
                            <label for="last-name-input">Last name:</label>
                            <b-form-input id="last-name-input" type="text" required v-model="lastName"></b-form-input>
                            <b-button type="submit">Create account</b-button>
                        </b-form>
                    </div>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
    import {authorizationService} from "../App";

    export default {
        name: "CreateAccount",
        methods: {
            createAccount: function () {
                if (this.password !== this.confirmPassword) {
                    alert("be");
                } else {
                    authorizationService.register(this.login, this.password, this.email, this.firstName, this.lastName)
                        .then(res => {
                            if (res.errors) {
                                alert("Register error!");
                            } else {
                                this.$router.push('/login');
                            }
                        })
                }

            }
        },
        data: function () {
            return {
                login: '',
                password: '',
                confirmPassword: '',
                email: '',
                firstName: '',
                lastName: ''

            }
        }
    }
</script>

<style scoped>
    .create-account {
        margin-bottom: 50px;
    }

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
        margin-top: 20px;
        border: 2px solid var(--colour4);
        background: none;
        color: #b9b9b9;
    }

    button:hover {
        border: 2px solid var(--colour4);
        background: none;
        text-shadow: 0 0 5px var(--colour5);
    }

    .create-account-panel {
        margin-top: 130px;
        background: var(--colour2);
        box-shadow: 0 0 13px 9px var(--colour4);
        padding: 30px 30px 80px 30px;
    }
</style>
