import { getJournal } from "@/app/api/journal";
import { getShelves } from "@/app/api/shelf";
import { useQuery } from "@tanstack/react-query";

export const useShelvesQuery = () => {
  return useQuery({
    queryKey: ["shelves"],
    queryFn: getShelves,
    staleTime: 60 * 1000,
  });
};

export const useJournalQuery = (jid: string) => {
  return useQuery({
    queryKey: ["journals", jid],
    queryFn: () => {
      return getJournal(jid);
    },
    staleTime: 60 * 1000,
  });
};
