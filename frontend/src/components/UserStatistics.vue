<template>
    <div class="user-statistics">
        <apex-chart type="donut" :options="options" :series="series"></apex-chart>
    </div>
</template>

<script>
    export default {
        name: "UserStatistics",
        props: {
            series: {
                type: Array,
                required: true
            },
            labels: {
                type: Array,
                required: true
            }
        },
        data() {
            return {
                options: {
                    chart: {
                        width: 380,
                        type: 'donut',
                    },
                    fill: {
                        type: "gradient"
                    },
                    colors: ['#64f2e6','#e21f25'],
                    labels: this.labels,
                    legend: {
                        formatter: function(val, opts) {
                            return val + " - " + opts.w.globals.series[opts.seriesIndex]
                        },
                        position: 'bottom'
                    }

                }
            }
        }
    }
</script>

<style scoped>

    /deep/ .apexcharts-legend-text{
        color: white!important;
    }
    /deep/ .apexcharts-tooltip-text-label, /deep/ .apexcharts-tooltip-text-value {
        color: black;
    }

</style>