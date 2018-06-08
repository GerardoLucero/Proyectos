Public Class Form1

    Dim A(3, 3) As Double
    Dim B(3, 3) As Double
    Dim C(3, 3) As Double


    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click

        A(0, 0) = Convert.ToDouble(TextBox1.Text)
        A(0, 1) = Convert.ToDouble(TextBox2.Text)
        A(0, 2) = Convert.ToDouble(TextBox3.Text)
        A(1, 0) = Convert.ToDouble(TextBox4.Text)
        A(1, 1) = Convert.ToDouble(TextBox5.Text)
        A(1, 2) = Convert.ToDouble(TextBox6.Text)
        A(2, 0) = Convert.ToDouble(TextBox7.Text)
        A(2, 1) = Convert.ToDouble(TextBox8.Text)
        A(2, 2) = Convert.ToDouble(TextBox9.Text)

        B(0, 0) = Convert.ToDouble(TextBox10.Text)
        B(0, 1) = Convert.ToDouble(TextBox11.Text)
        B(0, 2) = Convert.ToDouble(TextBox12.Text)
        B(1, 0) = Convert.ToDouble(TextBox13.Text)
        B(1, 1) = Convert.ToDouble(TextBox14.Text)
        B(1, 2) = Convert.ToDouble(TextBox15.Text)
        B(2, 0) = Convert.ToDouble(TextBox16.Text)
        B(2, 1) = Convert.ToDouble(TextBox17.Text)
        B(2, 2) = Convert.ToDouble(TextBox18.Text)



        For i As Integer = 0 To 3
            For j As Integer = 0 To 3
                C(i, j) = A(i, j) + B(i, j)
            Next
        Next


        TextBox19.Text = C(0, 0)
        TextBox20.Text = C(0, 1)
        TextBox21.Text = C(0, 2)
        TextBox22.Text = C(1, 0)
        TextBox23.Text = C(1, 1)
        TextBox24.Text = C(1, 2)
        TextBox25.Text = C(2, 0)
        TextBox26.Text = C(2, 1)
        TextBox27.Text = C(2, 2)


    End Sub
End Class
