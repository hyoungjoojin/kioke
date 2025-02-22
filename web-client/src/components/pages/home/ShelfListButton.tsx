import { Button } from "@/components/ui/button";
import { LibraryBig } from "lucide-react";
import Link from "next/link";

export default function ShelfListButton() {
  return (
    <Link href="/shelves" className="mx-2">
      <Button variant="outline">
        <LibraryBig />
      </Button>
    </Link>
  );
}
