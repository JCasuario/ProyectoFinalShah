<script setup>
    import divisorHorizontal from './divisorHorizontal.vue';

    defineProps({
        imagen: {
            type: String,
            required: true,
        },
        tit: {
            type: String,
            required: true
        },
        contenido: {
            type: String,
            required: true
        }
    })

</script>

<template>
    <div class="contenedorNoticia">
        <div class="contenedorImagen">
            <img :src="imagen" alt="imagenNotici">
        </div>
        <!-- <divisorHorizontal></divisorHorizontal> -->
        <div class="contenedorTexto">
            <h3 class="tit">{{ tit }}</h3>
            <p>{{ contenido }}</p>
        </div>

    
    </div>
</template>

<style scoped>
    .contenedorNoticia{
        padding: 10px;
        width: 25vw;
        height: 60vh;
        border-radius: 10px;
        background-color: white;
        box-shadow: 0 5px 8px black;
        display: flex;
        flex-direction: column;
        justify-content: center;
        transition: transform 1s;
        gap: 3px;
    }

    .contenedorImagen{
        min-height: 50%;
        overflow: hidden;
    }

    .contenedorTexto{
        max-height: 50%;
    }

    .contenedorNoticia .contenedorImagen img{
        width: 100%;
        height: 100%;
        border-radius: 5px;
        object-fit: cover;  
        object-position: top; 
    }

    .tit{
        font-weight: bolder;
    }

    @media (max-width: 1000px) {
        .contenedorNoticia{
            width: 100%;
        }
    }

    @media (hover: hover) {

        .contenedorNoticia:hover{
            transform: scale(1.15);
        }

    }
</style>
