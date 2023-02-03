import { useMediaQuery } from "@vueuse/core";

const isMobileScreen = useMediaQuery("(min-width: 500px)");

export { isMobileScreen };
