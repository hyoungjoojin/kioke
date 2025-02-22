import { Button } from "@/components/ui/button";
import { SquarePen } from "lucide-react";
import Link from "next/link";

export default function AddJournalButton() {
  return (
    <Link href="/journal/new">
      <Button variant="outline">
        <SquarePen />
      </Button>
    </Link>
  );
}
