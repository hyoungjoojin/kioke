import { useBoundStore } from "@/components/providers/StoreProvider";

export const useTransactionStatus = () => {
  const status = useBoundStore((state) => state.status);
  const setStatus = useBoundStore((state) => state.actions.setStatus);

  return {
    status,
    setStatus,
  };
};
