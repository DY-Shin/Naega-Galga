import { useMediaQuery } from "@vueuse/core";

const isMobileScreen = useMediaQuery("(max-width: 500px)");

export { isMobileScreen };
