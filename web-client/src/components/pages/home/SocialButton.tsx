import Link from "next/link";

export default function SocialButton() {
  return (
    <Link
      href="/social"
      className="text-sm hover:underline hover:cursor-pointer"
    >
      Social
    </Link>
  );
}
